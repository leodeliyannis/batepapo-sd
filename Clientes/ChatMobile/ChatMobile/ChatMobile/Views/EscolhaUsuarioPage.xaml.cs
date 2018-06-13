using ChatMobile.Models;
using ChatMobile.Views;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace ChatMobile
{
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class EscolhaUsuarioPage : ContentPage
	{
        Pesquisa pesquisa { get; set; }
        TcpClient TCP { get; set; }
        NetworkStream Stream { get; set; }
        StreamWriter StreamWriter { get; set; }
        public EscolhaUsuarioPage(ObservableCollection<Assunto> assuntos)
		{
			InitializeComponent ();
            pckAssunto.ItemsSource = assuntos;

            pckAssunto.SelectedIndexChanged += OnAssuntoSelecionado;
            lvUsuarios.ItemSelected += OnUsuarioSelecionado;
		}

        private async void OnUsuarioSelecionado(object sender, SelectedItemChangedEventArgs e)
        {
            bool b = await DisplayAlert("Conversar", "Inciar conversa?", "Sim", "Nao");
            if (b)
            {
                await Navigation.PushAsync(new ChatPage(lvUsuarios.SelectedItem as Usuario));
            }
            else
            {

            }
        }

        private async void OnAssuntoSelecionado(object sender, EventArgs e)
        {
            Console.WriteLine("Assunto");
            //buscar lista de usuarios no servidor P2P
            pesquisa = new Pesquisa{
                metodo = "pesquisa_topico",
                nome = (pckAssunto.SelectedItem as Assunto).Nome,
                token = App.Argumentos.token.token
            };
            IPAddress ip = IPAddress.Parse(App.Argumentos.ip);
            TCP = new TcpClient();
            Console.WriteLine("tenta conectar" + ip);
            TCP.Connect(ip, 10253);
            Console.WriteLine("Conectou");

            Stream = TCP.GetStream();
            StreamWriter = new StreamWriter(TCP.GetStream());
            var json = string.Empty;
            json = JsonConvert.SerializeObject(pesquisa);
            StreamWriter.WriteLine(json);
            StreamWriter.Flush();
            Console.Write("Flush");

            Byte[] data = new Byte[1024*8];

           
            String responseData = String.Empty;
            Int32 bytes = await Stream.ReadAsync(data, 0, data.Length);
            responseData = Encoding.UTF8.GetString(data, 0, bytes);

            //List<Usuario> usuarios = 

            lvUsuarios.ItemsSource = new ObservableCollection<Usuario>
            {
                new Usuario{Id = 0, Nome = "Felipe", IP = "192.168.43.12"}
            };
        }

        
    }
}