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

namespace ChatMobile
{
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class BuscaPage : ContentPage
	{
        TcpClient TCP { get; set; }
        NetworkStream Stream { get; set; }
        StreamWriter StreamWriter { get; set; }

        Thread Resposta { get; set; }
        public BuscaPage ()
		{
			InitializeComponent ();
           /* IPAddress ip = IPAddress.Parse("127.0.0.1");
            TCP = new TcpClient();
            TCP.Connect(ip, 8080);
            App.Conversando = true;
            Stream = TCP.GetStream();
            StreamWriter = new StreamWriter(TCP.GetStream());
            StreamWriter.AutoFlush = true;*/

        }
    }
}