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
