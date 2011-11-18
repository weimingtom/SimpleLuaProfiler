1. About SimpleLuaProfiler
	This is a simple profiler for Lua,
	base on Lua debug library (see debughook.lua)
	and Java ProcessBuilder class.
	This tool gets information from lua.exe stdout/stderr output,
	such as "[DEBUG]test/factorial.lua:6",
	and then count line hit times. 

2. Usage
	(1) Open lua script: open a lua script file.
	(2) Run lua script: run a lua script file.
	(3) Stop and clean log: stop running script and clean output log.
	(4) Source & Logs Tab: view profiler and output log.
	
3. Dependencies
	(1) The class StreamCopyThread is originally from graphviz-api, 
		by Kohsuke Kawaguchi
		http://java.net/projects/graphviz-api
	(2) GUI is base on SWT
		http://eclipse.org/swt/
	(3) Lua for Windows
		http://www.lua.org/
		http://code.google.com/p/luaforwindows/

4. Problems and Bugs
	(1) Only support single lua script.
	(2) SWT Table cut out space characters of lua script.
	(3) Only run one script at the same time.
	(4) Cannot specify argument for command line.
	(5) Current directory may be wrong.
	