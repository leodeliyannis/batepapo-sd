using ChatMobile.Models;
using ChatMobile.Views;
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

        private void OnAssuntoSelecionado(object sender, EventArgs e)
        {
            //buscar lista de usuarios no servidor P2P
            pesquisa = new Pesquisa{
                metodo = "pesquisa_topico",
                nome = (pckAssunto.SelectedItem as Assunto).Nome,
                token = App.Argumentos.token
            };
            IPAddress ip = IPAddress.Parse(App.Argumentos.ip);
            TCP = new TcpClient();
            TCP.Connect(ip, 10253);
            Stream = TCP.GetStream();
            StreamWriter = new StreamWriter(TCP.GetStream());
            StreamWriter.AutoFlush = true;

            lvUsuarios.ItemsSource = new ObservableCollection<Usuario>
            {
                new Usuario{Id = 0, Nome = "Felipe", IP = "192.168.43.12"}
            };
        }

        
    }
}