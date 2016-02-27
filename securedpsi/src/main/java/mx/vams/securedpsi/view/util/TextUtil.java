package mx.vams.securedpsi.view.util;

public class TextUtil {

	public static final String	lineSeparator	= System.getProperty( "line.separator" );

	public static boolean esNumero( String numero ) {
		try {
			Double.parseDouble( numero );
			return true;
		} catch( NumberFormatException nfe ) {
			return false;
		}
	}

	public static boolean esEntero( String numero ) {
		try {
			Integer.parseInt( numero );
			return true;
		} catch( NumberFormatException nfe ) {
			return false;
		}
	}

	public static String rellenaCadenasConCeros( String cadena, int campos ) {
		if( cadena != null ) {
			cadena = cadena.trim();

			if( cadena.length() < campos ) {
				for( int i = 0; i < campos; i++ ) {
					cadena = "0" + cadena;
					if( cadena.length() == campos )
						break;
				}
			}

			return cadena;
		}

		return null;
	}
}