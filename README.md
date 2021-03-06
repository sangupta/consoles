# consoles

Java-based implementations for various consoles types that implement a standard API and pave
way for building better command-line tools in Java. The following console modes are currently
available:

* Text: uses the underlying OS console for functionality
* Ansi: enhances underlying OS console for ANSI compatible functionality
* UI: a Swing based console that mimicks traditional consoles
* Tabbed: a Swing based multi-tab console, builds over `UI` console type 
* Remote: a remote console that connects to a server

## Aim

* JLine based pure console that works from a command-line shell of an operating system
* Swing based UI console that mimicks the shell of an operating system
* Consistent API for all console implementations

**This library is in very nascent unstable stage and should NOT be used.**

## Features

* Non-intrusive console implementation
* Easy replacement for System.in, System.out, System.err and Console 
* Support for mouse interactions to paste data 

## Versioning

For transparency and insight into our release cycle, and for striving to maintain backward compatibility, 
`consoles` will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

## License
	
Copyright (c) 2013-2016, Sandeep Gupta

The project uses various other libraries that are subject to their
own license terms. See the distribution libraries or the project
documentation for more details.

The entire source is licensed under the Apache License, Version 2.0 
(the "License"); you may not use this work except in compliance with
the LICENSE. You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
