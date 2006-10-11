package games.stendhal.client.update;

import games.stendhal.common.Version;

import java.util.Properties;

import marauroa.common.Log4J;

import org.apache.log4j.Logger;

/**
 * manages downloading and installing of updates
 *
 * @author hendrik
 */
public class UpdateManager {
	// TODO: fix URL after testing is completed
	private static final String SERVER_FOLDER = "http://localhost/stendhal/updates/";
	private static Logger logger = Logger.getLogger(UpdateManager.class);
	private Properties fileList = null;

	/**
	 * Connects to the server and loads a Property object which contains
	 * information about the files available for update.
	 */
	private void init() {
		HttpClient httpClient = new HttpClient(SERVER_FOLDER + "update.properties");
		fileList = httpClient.fetchProperties();
	}

	public void process() {
		init();
		if (fileList == null) {
			return;
		}
		
		fileList.list(System.out);
		logger.info(Version.VERSION);
		logger.info(fileList.getProperty("version." + Version.VERSION, "unkown"));
	}

	// debug code
	public static void main(String args[]) {
		Log4J.init("data/conf/log4j.properties");
		UpdateManager um = new UpdateManager();
		um.process();
	}
}
