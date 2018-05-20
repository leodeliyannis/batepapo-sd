﻿using ChatMobile.Models;
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
	public partial class EscolhaAssuntoPage : ContentPage
	{
		public EscolhaAssuntoPage ()
		{
			InitializeComponent ();
            InicializaTela();

            pckAssunto.SelectedIndexChanged += OnAssuntoSelecionado;
            lvUsuarios.ItemSelected += OnUsuarioSelecionado;
		}

        private async void OnUsuarioSelecionado(object sender, SelectedItemChangedEventArgs e)
        {
            bool b = await DisplayAlert("Conversar", "Inciar conversa?", "Sim", "Nao");
            if (b)
            {

            }
            else
            {

            }
        }

        private void OnAssuntoSelecionado(object sender, EventArgs e)
        {
            throw new NotImplementedException();
        }

        private void InicializaTela()
        {
            pckAssunto.ItemsSource = new ObservableCollection<Assunto>
            {
                new Assunto{Id=0, Nome="Eleição 2018"},
                new Assunto{Id=1, Nome="Segurança Publica"},
                new Assunto{Id=2, Nome="Preço Gasolina"},
                new Assunto{Id=3, Nome="Sistema Nacional de Saude"},
                new Assunto{Id=4, Nome="Aposentadoria"},
                new Assunto{Id=5, Nome="Traição"},
                new Assunto{Id=6, Nome="Multas de Transito"},
                new Assunto{Id=7, Nome="Educação"}
            };
        }
    }
}