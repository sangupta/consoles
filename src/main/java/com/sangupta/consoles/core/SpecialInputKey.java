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

package com.sangupta.consoles.core;

/**
 * Enumeration that defines special keys as present on the
 * keyboard.
 * 
 * @author sangupta
 *
 */
public enum SpecialInputKey {
	
	// arrow keys
	LEFT_ARROW,
	RIGHT_ARROW,
	UP_ARROW,
	DOWN_ARROW,
	
	// insert-block
	INSERT,
	DELETE,
	HOME,
	END,
	PAGE_UP,
	PAGE_DOWN,
	
	// function keys
	F1, F2, F3, F4,
	F5, F6, F7, F8,
	F9, F10,
	F11, F12;
	
}