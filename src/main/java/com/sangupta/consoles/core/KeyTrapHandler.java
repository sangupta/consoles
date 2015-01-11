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

package com.sangupta.consoles.core;

/**
 * Contract for all key trap handlers that need to handle
 * any key press inside the console.
 * 
 */
public interface KeyTrapHandler {
	
	/**
	 * Handle a key press for which this handler was added as a trap.
	 * 
	 * @param key
	 * 
	 * @return <code>true</code> if the event needs to be bubbled up and handled
	 * by other handlers as well, <code>false</code> otherwise.
	 * 
	 */
	public boolean handleKeyInvocation(InputKey key);

}