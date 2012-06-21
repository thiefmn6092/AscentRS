package net.thiefmn6092.ascent.script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A management class for Python scripting.
 * 
 * @author thiefmn6092
 * 
 */
public class ScriptManager {
	
	/**
	 * The SLF4J {@link org.slf4j.Logger} instance.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ScriptManager.class);

	/**
	 * The ScriptManager singleton instance.
	 */
	private static final ScriptManager SINGLETON = new ScriptManager();

	/**
	 * The {@link org.python.util.PythonInterpreter} instance.
	 */
	private final PythonInterpreter interpreter = new PythonInterpreter();

	/**
	 * The number of scripts that have been loaded.
	 */
	private int scriptCount = 0;

	/**
	 * The class constructor.
	 */
	private ScriptManager() {
		/*
		 * Cannot be instantiated by other classes.
		 */
	}

	/**
	 * Recursively loads scripts from the given directory.
	 * 
	 * @param path
	 *            The path to the directory.
	 * @throws FileNotFoundException
	 *             If the file could not be found.
	 */
	public void loadScripts(String path) throws FileNotFoundException {
		File file = new File(path);
		if (!file.isDirectory()) {
			throw new IllegalArgumentException("Path is not a directory!");
		}
		for (File $file : file.listFiles()) {
			if ($file.isDirectory()) {
				loadScripts($file.getPath());
				continue;
			} else if (!$file.getName().endsWith(".py")) {
				continue;
			}
			InputStream in = new FileInputStream($file);
			interpreter.execfile(in);
			scriptCount += 1;
		}
	}

	/**
	 * Calls the function with the given identifier and arguments.
	 * 
	 * @param identifier
	 *            The function identifier.
	 * @param arguments
	 *            The function arguments.
	 * @return The value returned by the function.
	 */
	public Object invoke(String identifier, Object... arguments) {
		PyFunction function = (PyFunction) interpreter.get(identifier);
		if (function == null) {
			logger.info("PyFunction \"" + identifier + "\" does not exist!");
			return null;
		}
		PyObject[] args = Py.javas2pys(arguments);
		PyObject value = function.__call__(args);
		return Py.tojava(value, Object.class);
	}

	/**
	 * Gets the ScriptManager singleton instance.
	 * 
	 * @return The singleton.
	 */
	public static ScriptManager getSingleton() {
		return SINGLETON;
	}

	/**
	 * Gets the number of scripts that have been loaded.
	 * 
	 * @return The number of scripts.
	 */
	public int getScriptCount() {
		return scriptCount;
	}

}
