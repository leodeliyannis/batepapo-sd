using System;
using System.Net.Sockets;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

[assembly: XamlCompilation (XamlCompilationOptions.Compile)]
namespace ChatMobile
{
	public partial class App : Application
	{
        public static bool Conversando { get; set; }
        public App ()
		{
			InitializeComponent();
            Conversando = false;

			MainPage = new MainPage();
		}

		protected override void OnStart ()
		{
			// Handle when your app starts
		}

		protected override void OnSleep ()
		{
			// Handle when your app sleeps
		}

		protected override void OnResume ()
		{
			// Handle when your app resumes
		}
	}
}
