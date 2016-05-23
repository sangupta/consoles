/**
 *
 * consoles - Java based console terminals
 * Copyright (c) 2013-2016, Sandeep Gupta
 * 
 * http://www.sangupta/projects/consoles
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sangupta.consoles;

/**
 * Constants for the entire {@link Consoles} project.
 * 
 * @author sangupta
 *
 */
public interface ConsolesConstants {
	
	/**
	 * The system new-line separator
	 */
	public static final String NEW_LINE = System.getProperty("line.separator");

	/**
	 * Default number of rows in a console if not specified
	 */
	public static final int DEFAULT_CONSOLE_ROWS = 25;
	
	/**
	 * Default number of columns in a console if not specified
	 */
	public static final int DEFAULT_CONSOLE_COLUMNS = 80;
	
}
