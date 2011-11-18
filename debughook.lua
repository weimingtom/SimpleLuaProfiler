function trace(event, line)
	--local s = debug.getinfo(2).short_src
	local s = debug.getinfo(2).source
	print('[DEBUG]' .. s .. ':' ..line)
	--print("---")
	io.stdout:flush()
	--os.execute('ping -n 2 127.0.0.1 > nul')
end
io.stdout:setvbuf('no')
debug.sethook(trace, 'l')
