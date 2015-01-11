/**
 *
 * consoles - Java based console terminals
 * Copyright (c) 2013-2015, Sandeep Gupta
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

package com.sangupta.consoles.constants;

/**
 * Contains constant ANSI sequences that can be printed onto
 * the screen directly.
 * 
 * @author sangupta
 *
 */
public interface AnsiSequences {

	public static final String CLEAR_SCREEN = "2J";
	
	public static final String TOP_LEFT_SCREEN = "1:1H";
	
	public static final String CLEAR_CURRENT_LINE = "K";
	
}