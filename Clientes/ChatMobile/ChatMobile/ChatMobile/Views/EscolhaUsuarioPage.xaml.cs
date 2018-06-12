using ChatMobile.Models;
using ChatMobile.Views;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace ChatMobile
{
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class EscolhaUsuarioPage : ContentPage
	{
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
            lvUsuarios.ItemsSource = new ObservableCollection<Usuario>
            {
                new Usuario{Id = 0, Nome = "Felipe", IP = "192.168.43.12"}
            };
        }

        
    }
}