<<<<<<< HEAD:apiS/ChatMobile/ChatMobile/ChatMobile/Views/MainPage.xaml.cs
<<<<<<< HEAD:ChatMobile/ChatMobile/ChatMobile/Views/MainPage.xaml.cs
﻿using ChatMobile.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace ChatMobile
{
	public partial class MainPage : ContentPage
	{
        bool _eleicao { get; set; }
        bool _seguranca { get; set; }
        bool _gasolina { get; set; }
        bool _saude { get; set; }
        bool _aposentadoria { get; set; }
        bool _traicao { get; set; }
        bool _multas { get; set; }
        bool _educacao { get; set; }
        List<string> topicos { get; set; }
        public MainPage()
        {
            InitializeComponent();
            InicializaListaSwitch();
            
            aposentadoria.Toggled += (s, e) => _aposentadoria = !_aposentadoria;
            educacao.Toggled += (s, e) => _educacao = !_educacao;
            eleicao.Toggled += (s, e) => _eleicao = !_eleicao;
            gasolina.Toggled += (s, e) => _gasolina = !_gasolina;
            multas.Toggled += (s, e) => _multas = !_multas;
            saude.Toggled += (s, e) => _saude = !_saude;
            seguranca.Toggled += (s, e) => _seguranca = !_seguranca;
            traicao.Toggled += (s, e) => _traicao = !_traicao;

            proximaBtn.Clicked += async (s, e) => await Navigation.PushAsync(new EscolhaUsuarioPage());
            loginBtn.Clicked += OnClick;
        }
        private void InicializaListaSwitch()
        {
            _aposentadoria = aposentadoria.IsToggled;
            _educacao = educacao.IsToggled;
            _eleicao = eleicao.IsToggled;
            _gasolina = gasolina.IsToggled;
            _multas = multas.IsToggled;
            _saude = saude.IsToggled;
            _seguranca = seguranca.IsToggled;
            _traicao = traicao.IsToggled;
        }

        private void OnClick(object sender, EventArgs e)
        {
            if (ValidarCampos())
            {
                if (Conectar())
                    Application.Current.MainPage = new MDPage();
            }
            else
            {
                DisplayAlert("Aviso", "Preencher todos os campos", "Fechar");
            }
        }

        private bool Conectar()
        {
            CompletaListaTopicos();
            Token token = new Token();

            UsuarioInput usuarioInput = new UsuarioInput(user.Text, password.Text, topicos);
            Argumentos argumentos = new Argumentos();
            argumentos.Modo = "c";
            argumentos.Ip = serverIp.Text;
            argumentos.Porta = 10453;
            argumentos.UsuarioInput = usuarioInput;
            argumentos.Token = token;


            /*Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);
            IPAddress ip = IPAddress.Parse(serverIp.Text);
            string[] str = new[] { user.Text + " - " + password.Text };

            byte[] sendbuf = Encoding.ASCII.GetBytes(str[0]);
            IPEndPoint ep = new IPEndPoint(ip, 10453);
            
            s.SendTo(sendbuf, ep);*/
            return true;
        }

        private void CompletaListaTopicos()
        {
            topicos = new List<string>();
            if (aposentadoria.IsToggled)
                topicos.Add("Aposentadoria");
            if (educacao.IsToggled)
                topicos.Add("Educação");
            if (eleicao.IsToggled)
                topicos.Add("Eleições 2018");
            if (gasolina.IsToggled)
                topicos.Add("Preço da gasolina");
            if (multas.IsToggled)
                topicos.Add("Multas de trânsito");
            if (saude.IsToggled)
                topicos.Add("Sistema nacional de saúde");
            if (seguranca.IsToggled)
                topicos.Add("Segurança pública");
            if (traicao.IsToggled)
                topicos.Add("Traição");
            
        }

        private bool ValidarCampos()
        {
            return user.Text != string.Empty &&
                   password.Text != string.Empty &&
                   serverIp.Text != string.Empty;
        }
    }

    public class UsuarioInput
    {
        string Usuario { get; set; }
        string Senha { get; set; }
        List<string> Topicos { get; set; }
        public UsuarioInput(string usuario, string senha, List<string> lista)
        {
            Usuario = usuario;
            Senha = senha;
            Topicos = lista;
        }

    }
}
=======
﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace ChatMobile
{
	public partial class MainPage : ContentPage
	{
        public MainPage()
        {
            InitializeComponent();

            loginBtn.Clicked += OnClick;
        }

        private void OnClick(object sender, EventArgs e)
        {
            if (ValidarCampos())
            {
                if (Conectar())
                    Application.Current.MainPage = new MDPage();
            }
            else
            {
                DisplayAlert("Aviso", "Preencher todos os campos", "Fechar");
            }
        }

        private bool Conectar()
        {
            /*Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);
            IPAddress ip = IPAddress.Parse(serverIp.Text);
            string[] str = new[] { user.Text + " - " + password.Text };
            byte[] sendbuf = Encoding.ASCII.GetBytes(str[0]);
            IPEndPoint ep = new IPEndPoint(ip, 11223);

            s.SendTo(sendbuf, ep);*/
            return true;
        }

        private bool ValidarCampos()
        {
            return user.Text != string.Empty &&
                   password.Text != string.Empty &&
                   serverIp.Text != string.Empty;
        }
    }
}
>>>>>>> 2b2f015847af4f673769248880132ac21487409f:apiS/ChatMobile/ChatMobile/ChatMobile/Views/MainPage.xaml.cs
=======
﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace ChatMobile
{
	public partial class MainPage : ContentPage
	{
        public MainPage()
        {
            InitializeComponent();

            loginBtn.Clicked += OnClick;
        }

        private void OnClick(object sender, EventArgs e)
        {
            if (ValidarCampos())
            {
                if (Conectar())
                    Application.Current.MainPage = new MDPage();
            }
            else
            {
                DisplayAlert("Aviso", "Preencher todos os campos", "Fechar");
            }
        }

        private bool Conectar()
        {
            /*Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);
            IPAddress ip = IPAddress.Parse(serverIp.Text);
            string[] str = new[] { user.Text + " - " + password.Text };
            byte[] sendbuf = Encoding.ASCII.GetBytes(str[0]);
            IPEndPoint ep = new IPEndPoint(ip, 11223);

            s.SendTo(sendbuf, ep);*/
            return true;
        }

        private bool ValidarCampos()
        {
            return user.Text != string.Empty &&
                   password.Text != string.Empty &&
                   serverIp.Text != string.Empty;
        }
    }
}
>>>>>>> parent of 744aee5... Ajustes:ChatMobile/ChatMobile/ChatMobile/Views/MainPage.xaml.cs
