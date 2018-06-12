using ChatMobile.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
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
        ObservableCollection<Assunto> Assuntos { get; set; }
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
                    Application.Current.MainPage = new EscolhaUsuarioPage(Assuntos);
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
            argumentos.porta = 10553;
            argumentos.debug = true;
            argumentos.dh = 1024;
            argumentos.aes = 128;
            argumentos.usuarioInput = usuario;
            argumentos.token = token;

            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);
            IPAddress ip = IPAddress.Parse(serverIp.Text);

            var json = string.Empty;
            json = JsonConvert.SerializeObject(usuario);
            Console.WriteLine(json);

            byte[] sendBuf = Encoding.ASCII.GetBytes(json);
            IPEndPoint ep = new IPEndPoint(ip, 10553);

            s.SendTo(sendBuf, ep);
            byte[] recBuf = new byte[2048];
            s.Receive(recBuf);
            

            s.Close();

            return true;
        }

        private List<string> PreencheLista()
        {
            Assuntos = new ObservableCollection<Assunto>();
            List<string> lista = new List<string>();
            if (_eleicao)
            {
                lista.Add("Eleição 2018");
                Assuntos.Add(new Assunto { Id = 0, Nome = "Eleição 2018" });
            }
                
            if(_seguranca)
            {
                lista.Add("Segurança Publica");
                Assuntos.Add(new Assunto { Id = 1, Nome = "Segurança Publica" });
            }
            if (_gasolina)
            {
                lista.Add("Preço Gasolina");;
                Assuntos.Add(new Assunto { Id = 2, Nome = "Preço Gasolina" });
            }
            if (_saude)
            {
                lista.Add("Sistema Nacional de Saude");;
                Assuntos.Add(new Assunto { Id = 3, Nome = "Sistema Nacional de Saude" });
            }
            if (_aposentadoria)
            {
                lista.Add("Aposentadoria");;
                Assuntos.Add(new Assunto { Id = 4, Nome = "Aposentadoria" });
            }
            if (_traicao)
            {
                lista.Add("Traição");;
                Assuntos.Add(new Assunto { Id = 5, Nome = "Traição" });
            }
            if (_multas)
            {
                lista.Add("Multas de Transito");;
                Assuntos.Add(new Assunto { Id = 6, Nome = "Multas de Transito" });
            }
            if (_educacao)
            {
                lista.Add("Educação");;
                Assuntos.Add(new Assunto { Id = 7, Nome = "Educação" });
            }
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
