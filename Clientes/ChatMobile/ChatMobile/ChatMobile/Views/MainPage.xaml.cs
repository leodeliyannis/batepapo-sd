using ChatMobile.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
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
            List<string> lista = PreencheLista();
            Token token = new Token();

            UsuarioInput usuario = new UsuarioInput { usuario = user.Text, senha = password.Text, topicos = lista };
            Argumentos argumentos = new Argumentos();

            argumentos.modo = "c";
            argumentos.ip = serverIp.Text;
            argumentos.porta = 10453;
            argumentos.debug = true;
            argumentos.dh = 1024;
            argumentos.aes = 128;
            argumentos.usuarioInput = usuario;
            argumentos.token =token;

            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);
            IPAddress ip = IPAddress.Parse(serverIp.Text);

            var json = string.Empty;
            json = JsonConvert.SerializeObject(usuario);
            
            //string str = user.Text + " - " + password.Text;
            byte[] sendbuf = Encoding.ASCII.GetBytes(json);
            IPEndPoint ep = new IPEndPoint(ip, 10553);
            
            s.SendTo(sendbuf, ep);
            return true;
        }

        private List<string> PreencheLista()
        {
            List<string> lista = new List<string>();
            if(_eleicao)
                lista.Add("Eleições 2018");
            if(_seguranca)
                lista.Add("Segurança pública");
            if(_gasolina)
                lista.Add("Preço da gasolina");
            if(_saude)
                lista.Add("Sistema nacional de saúde ");
            if(_aposentadoria)
                lista.Add("Aposentadoria");
            if(_traicao)
                lista.Add("Traição");
            if(_multas)
                lista.Add("Multas de trânsito");
            if(_educacao)
                lista.Add("Educação");
            return lista;
        }

        private bool ValidarCampos()
        {
            return user.Text != string.Empty &&
                   password.Text != string.Empty &&
                   serverIp.Text != string.Empty;
        }
    }
}
