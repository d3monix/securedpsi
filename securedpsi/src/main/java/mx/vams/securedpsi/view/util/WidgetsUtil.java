package mx.vams.securedpsi.view.util;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class WidgetsUtil {

	public static final int	TECLA_ENTER			= 13;
	public static final int	TECLA_ESC			= SWT.ESC;
	public static final int	TECLA_FLECHA_ABAJO	= 16777218;
	public static final int	TECLA_TAB			= 9;
	public static final int	TECLA_INTRO			= 16777296;

	public static void inicializarShellCentrado( Shell shell ) {

		Monitor primary = shell.getDisplay().getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();

		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;

		shell.setLocation( x, y );

		shell.open();
		shell.layout();

		while( !shell.isDisposed() ) {
			if( !shell.getDisplay().readAndDispatch() ) {
				shell.getDisplay().sleep();
			}
		}
	}

	public static void inicializarShellMaximizado( Shell shell ) {

		shell.setMaximized( true );
		shell.open();
		shell.layout();

		while( !shell.isDisposed() ) {
			if( !shell.getDisplay().readAndDispatch() ) {
				shell.getDisplay().sleep();
			}
		}
	}

	public static void inicializarShell( Shell shell ) {

		shell.open();
		shell.layout();

		while( !shell.isDisposed() ) {
			if( !shell.getDisplay().readAndDispatch() ) {
				shell.getDisplay().sleep();
			}
		}
	}

	public static void openDialog( Shell parent, String titulo, String mensaje, int swtIconType ) {
		MessageBox messageBox = new MessageBox( parent, SWT.OK | swtIconType );
		messageBox.setText( titulo );
		messageBox.setMessage( mensaje );
		messageBox.open();
	}

	public static boolean openYesNoDialog( Shell parent, String titulo, String mensaje, int swtIconType ) {
		MessageBox messageBox = new MessageBox( parent, SWT.YES | SWT.NO | SWT.CANCEL | swtIconType );
		messageBox.setText( titulo );
		messageBox.setMessage( mensaje );

		if( messageBox.open() == SWT.YES )
			return true;
		else
			return false;
	}

	public static ComboViewer configurarIMapeableCombo( Combo combo, boolean mostrarCodigo ) {

		IMapeableComboConfig config = new IMapeableComboConfig();
		config.setMostrarCodigo( mostrarCodigo );

		ComboViewer viewer = new ComboViewer( combo );
		viewer.setLabelProvider( config );
		viewer.setContentProvider( config );

		return viewer;
	}

	@SuppressWarnings( "rawtypes" )
	public static int existeIMapeableEnLista( IMapeable iMapeable, List lista ) {
		if( iMapeable != null && lista != null ) {

			for( int i = 0; i < lista.size(); i++ ) {
				IMapeable im = (IMapeable)lista.get( i );

				if( im.getCodigo().trim().equals( iMapeable.getCodigo().trim() ) )
					return i;
			}
		}

		return -1;
	}

	@SuppressWarnings( "unchecked" )
	public static boolean setIMapeableEnCombo( ComboViewer viewer, IMapeable iMapeable ) {

		List< IMapeable > lista = (List< IMapeable >)viewer.getInput();

		if( lista != null && iMapeable != null ) {
			for( int i = 0; i < lista.size(); i++ ) {
				IMapeable im = (IMapeable)lista.get( i );

				if( im.getCodigo().trim().equals( iMapeable.getCodigo().trim() ) ) {
					viewer.getCombo().select( i );

					return true;
				}
			}
		}

		return false;
	}

	@SuppressWarnings( "rawtypes" )
	public static int eliminarIMapeableEnLista( IMapeable iMapeable, List lista ) {
		if( iMapeable != null && lista != null ) {

			for( int i = 0; i < lista.size(); i++ ) {
				IMapeable im = (IMapeable)lista.get( i );

				if( im.getCodigo().trim().equals( iMapeable.getCodigo().trim() ) ) {
					lista.remove( i );
					return i;
				}
			}
		}

		return -1;
	}

	@SuppressWarnings( "rawtypes" )
	public static void resetCombo( ComboViewer viewer ) {
		List lista = (List)viewer.getInput();
		Combo combo = viewer.getCombo();
		viewer.setInput( null );
		combo.select( -1 );
		viewer.setInput( lista );
	}

	public static IMapeable getIMapeableDesdeCombo( ComboViewer viewer ) {
		IMapeable mapeable = null;
		Combo combo = viewer.getCombo();

		if( combo.getSelectionIndex() > -1 ) {
			mapeable = (IMapeable)viewer.getElementAt( combo.getSelectionIndex() );
		}

		return mapeable;
	}

	public static VerifyListener getListenerMayusculas() {
		return new CampoMayusculas();
	}

	public static VerifyListener getListenerCampoNumericoEntero( Text campo ) {
		return new CampoNumericoEntero( campo );
	}

	public static VerifyListener getListenerCampoNumericoFlotante( Text campo ) {
		return new CampoNumericoFlotante( campo );
	}

	private static class CampoMayusculas implements VerifyListener {
		private static final long	serialVersionUID	= -8216231294193300021L;

		@Override
		public void verifyText( VerifyEvent e ) {
			e.text = e.text.toUpperCase();
		}
	}

	public static class CampoNumericoEntero implements VerifyListener {
		private static final long	serialVersionUID	= -2191162522769068080L;

		private Text				campo;

		public CampoNumericoEntero( Text campo ) {
			this.campo = campo;
		}

		@Override
		public void verifyText( VerifyEvent e ) {

			// Si no es un número no pasa!
			if( !TextUtil.esEntero( campo.getText() + e.text ) ) {
				e.doit = false;
				return;
			}
		}
	}

	public static class CampoNumericoFlotante implements VerifyListener {
		private static final long	serialVersionUID	= 4623499737719422384L;

		private Text				campo;
		private boolean				deshabilitar;

		public CampoNumericoFlotante( Text campo ) {
			this.campo = campo;
		}

		public CampoNumericoFlotante( Text campo, NumberFormat formatter ) {
			this.campo = campo;
		}

		@Override
		public void verifyText( VerifyEvent e ) {

			if( !deshabilitar ) {
				// Si no es un número no pasa!
				if( !TextUtil.esNumero( campo.getText() + e.text ) ) {
					e.doit = false;
					return;
				}

				// Si es número y contiene c o d al final (Legal en la
				// definición de
				// flotantes en java) no pasa
				else if( e.text.contains( "d" ) || e.text.contains( "f" ) || tieneMasDeDosDecimales( campo.getText() + e.text ) ) {
					e.doit = false;
					return;
				}
			}
		}

		private boolean tieneMasDeDosDecimales( String cadena ) {
			int indexDePuntoDecimal = cadena.indexOf( "." );

			if( indexDePuntoDecimal > -1 ) {
				if( cadena.substring( indexDePuntoDecimal, cadena.length() ).length() > 3 )
					return true;
			}

			return false;
		}

		public void deshabilitarVerifyListener( boolean deshabilitar ) {
			this.deshabilitar = deshabilitar;
		}
	}

	public static InputDialog getInputDialog( Shell parent, String titulo, String instruccion, String valorInicial ) {
		return new DialogoDeEntrada( parent, titulo, instruccion, valorInicial, null );
	}

	private static class DialogoDeEntrada extends InputDialog {
		private static final long	serialVersionUID	= 2978228739712757112L;

		public DialogoDeEntrada( Shell parentShell, String dialogTitle, String dialogMessage, String initialValue, IInputValidator validator ) {
			super( parentShell, dialogTitle, dialogMessage, initialValue, validator );
		}

		@Override
		protected int getInputTextStyle() {
			return SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP;
		}

		@Override
		protected Control createDialogArea( Composite parent ) {
			Control control = super.createDialogArea( parent );
			((GridData)getText().getLayoutData()).heightHint = 50;

			return control;
		}

		protected void createButtonsForButtonBar( Composite parent ) {
			super.createButtonsForButtonBar( parent );

			getButton( IDialogConstants.OK_ID ).setText( "Aceptar" );
			getButton( IDialogConstants.CANCEL_ID ).setText( "Cancelar" );
		}
	}

	public static Date getDateFromDateTime( DateTime dt ) {
		Calendar c = Calendar.getInstance();

		c.set( Calendar.SECOND, 0 );
		c.set( Calendar.MINUTE, dt.getMinutes() );
		c.set( Calendar.HOUR_OF_DAY, dt.getHours() );
		c.set( Calendar.DAY_OF_MONTH, dt.getDay() );
		c.set( Calendar.MONTH, dt.getMonth() );
		c.set( Calendar.YEAR, dt.getYear() );

		return c.getTime();
	}

	public static void setDateToDateTime( DateTime dt, Date date ) {
		Calendar c = Calendar.getInstance();
		c.setTime( date );

		dt.setSeconds( 0 );
		dt.setMinutes( Calendar.MINUTE );
		dt.setHours( Calendar.HOUR_OF_DAY );
		dt.setDay( c.get( Calendar.DAY_OF_MONTH ) );
		dt.setMonth( c.get( Calendar.MONTH ) );
		dt.setYear( c.get( Calendar.YEAR ) );
	}

	// public static PrintService getServicioDeImpresionSWT( Shell parent,
	// String nombreDeImpresora ) {
	//
	// PrintService [] printServices = PrintServiceLookup.lookupPrintServices(
	// null, null );
	//
	// if( nombreDeImpresora != null && !nombreDeImpresora.trim().isEmpty() ) {
	//
	// for( PrintService impresora : printServices ) {
	// if( impresora.getName().trim().equals( nombreDeImpresora.trim() ) ) {
	// return impresora;
	// }
	// }
	// }
	// else {
	// PrintDialog dlg = new PrintDialog( parent );
	//
	// dlg.setScope( PrinterData.SELECTION );
	// PrinterData printerData = dlg.open();
	//
	// if( printerData != null ) {
	//
	// for( PrintService impresora : printServices ) {
	// if( impresora.getName().trim().equals( printerData.name.trim() ) ) {
	// return impresora;
	// }
	// }
	// }
	// }
	//
	// return null;
	// }
}