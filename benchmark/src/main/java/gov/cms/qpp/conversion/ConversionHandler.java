package gov.cms.qpp.conversion;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


public class ConversionHandler extends DefaultHandler {

	/**
	 * Receive notification of a parser warning.
	 *
	 * <p>The default implementation does nothing.  Application writers
	 * may override this method in a subclass to take specific actions
	 * for each warning, such as inserting the message in a log file or
	 * printing it to the console.</p>
	 *
	 * @param e The warning information encoded as an exception.
	 * @exception SAXException Any SAX exception, possibly
	 *            wrapping another exception.
	 * @see org.xml.sax.ErrorHandler#warning
	 * @see SAXParseException
	 */
	public void warning (SAXParseException e)
			throws SAXException
	{
		System.out.println("warning");
		e.printStackTrace(System.out);
	}


	/**
	 * Receive notification of a recoverable parser error.
	 *
	 * <p>The default implementation does nothing.  Application writers
	 * may override this method in a subclass to take specific actions
	 * for each error, such as inserting the message in a log file or
	 * printing it to the console.</p>
	 *
	 * @param e The error information encoded as an exception.
	 * @exception SAXException Any SAX exception, possibly
	 *            wrapping another exception.
	 * @see org.xml.sax.ErrorHandler#warning
	 * @see SAXParseException
	 */
	public void error (SAXParseException e)
			throws SAXException
	{
		System.out.println("error");
		e.printStackTrace(System.out);
	}


	/**
	 * Report a fatal XML parsing error.
	 *
	 * <p>The default implementation throws a SAXParseException.
	 * Application writers may override this method in a subclass if
	 * they need to take specific actions for each fatal error (such as
	 * collecting all of the errors into a single report): in any case,
	 * the application must stop all regular processing when this
	 * method is invoked, since the document is no longer reliable, and
	 * the parser may no longer report parsing events.</p>
	 *
	 * @param e The error information encoded as an exception.
	 * @exception SAXException Any SAX exception, possibly
	 *            wrapping another exception.
	 * @see org.xml.sax.ErrorHandler#fatalError
	 * @see SAXParseException
	 */
	public void fatalError (SAXParseException e)
			throws SAXException
	{
		System.out.println("fatal");
		e.printStackTrace(System.out);
		throw e;
	}
}
