package com.mumfrey.worldeditcui.debug;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mumfrey.worldeditcui.InitializationFactory;
import com.mumfrey.worldeditcui.WorldEditCUIController;
import com.mumfrey.worldeditcui.exceptions.InitializationException;
import com.mumfrey.worldeditcui.util.ConsoleLogFormatter;

import static com.mumfrey.worldeditcui.config.CUIConfiguration.CONFIG_PATH;

/**
 * Debugging helper class
 *
 * @author yetanotherx
 *
 */
public class CUIDebug implements InitializationFactory
{

	protected WorldEditCUIController controller;
	protected File debugFile;
	protected boolean debugMode = false;
	protected final static Logger logger = Logger.getLogger("WorldEditCUI");

	public CUIDebug(WorldEditCUIController controller)
	{
		this.controller = controller;
	}

	@Override
	public void initialize() throws InitializationException
	{

		ConsoleLogFormatter formatter = new ConsoleLogFormatter();
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(formatter);

		logger.setUseParentHandlers(false);
		logger.addHandler(handler);

		try
		{
			this.debugFile = CONFIG_PATH.resolve("worldeditcui.debug.log").toFile();
			this.debugMode = this.controller.getConfiguration().isDebugMode();

			if (this.debugMode)
			{
				FileHandler newHandler = new FileHandler(this.debugFile.getAbsolutePath());
				newHandler.setFormatter(formatter);
				logger.addHandler(newHandler);
			}

		}
		catch (IOException e)
		{
			e.printStackTrace(System.err);
			throw new InitializationException();
		}

	}

	/**
	 * Shows a message if debug mode is true
	 * @param message
	 */
	public void debug(String message)
	{
		if (this.debugMode)
		{
			logger.info("WorldEditCUI Debug - " + message);
		}
	}

	public void info(String message)
	{
		logger.info(message);
	}

	public void info(String message, Throwable e)
	{
		logger.log(Level.INFO, message, e);
	}
}
