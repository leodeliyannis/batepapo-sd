using ChatMobile.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace ChatMobile.Views
{
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class ChatPage : ContentPage
	{
        TcpClient TCP { get; set; }
        Usuario Usuario { get; set; }
        NetworkStream Stream { get; set; }
        StreamWriter StreamWriter { get; set; }

        Thread Resposta { get; set; }

        public ChatPage (Usuario usuario)
		{
			InitializeComponent ();

            Usuario = usuario;
            IPAddress ip = IPAddress.Parse(usuario.IP);
            TCP = new TcpClient();
            TCP.Connect(ip, 2017);
            App.Conversando = true;            
            Stream = TCP.GetStream();
            StreamWriter = new StreamWriter(TCP.GetStream()); 
            StreamWriter.AutoFlush = true;
            
            Resposta = new Thread(Escutando);
            Resposta.Start();
                                    
            btnSend.Clicked += OnSendClicked;            
        }

        private async void Escutando(object obj)
        {
            Byte[] data = new Byte[1024 * 8]; 
            while (App.Conversando)
            {
                String responseData = String.Empty;
                Int32 bytes = await Stream.ReadAsync(data, 0, data.Length); 
                responseData = System.Text.Encoding.UTF8.GetString(data, 0, bytes);
                var label = AddLabel(Color.Blue, responseData);
                Device.BeginInvokeOnMainThread(async () =>
                {
                    stack.Children.Add(label);
                    await scroll.ScrollToAsync(stack, ScrollToPosition.End, false);
                });
            }
            TCP.Close();
        }

        private async void OnSendClicked(object sender, EventArgs e)
        {
            if (TCP.Connected)
            {
                var label = AddLabel(Color.ForestGreen, entTxt.Text);
                stack.Children.Add(label);
                StreamWriter.WriteLine(entTxt.Text);
                await scroll.ScrollToAsync(stack, ScrollToPosition.End, false);

                entTxt.Text = string.Empty;
            }
            else
            {
                await DisplayAlert("Aviso", "App desconectado", "Ok");
            }
            
        }

        Label AddLabel(Color cor, string str) 
        {
            return new Label
            {
                Text = str,
                TextColor = Color.White,
                BackgroundColor = cor,
                HeightRequest = 30
            };
        }
    }
}