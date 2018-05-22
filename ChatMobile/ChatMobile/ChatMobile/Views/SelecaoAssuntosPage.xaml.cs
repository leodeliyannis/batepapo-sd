using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace ChatMobile
{
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class SelecaoAssuntosPage : ContentPage
	{
        bool _eleicao { get; set; }
        bool _seguranca { get; set; }
        bool _gasolina { get; set; }
        bool _saude { get; set; }
        bool _aposentadoria { get; set; }
        bool _traicao { get; set; }
        bool _multas { get; set; }
        bool _educacao { get; set; }
        public SelecaoAssuntosPage ()
		{
			InitializeComponent ();
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
    }
}