package com.iteye.weimingtom.luaprofiler;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * @see graphviz-api
 * @see http://java.net/projects/graphviz-api
 * @see http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html?page=4
 * 
 *      {@link Thread} that copies {@link InputStream} to {@link OutputStream}.
 * 
 * @author Kohsuke Kawaguchi
 */
class StreamCopyThread extends Thread {
    private final InputStream in;
    private final OutputStream out;
    private final StringBuffer buffer;

    public StreamCopyThread(String threadName, InputStream in,
	    OutputStream out, StringBuffer buffer) {
	super(threadName);
	this.in = in;
	this.out = out;
	this.buffer = buffer;
    }

    public void run() {
	try {
	    try {
		byte[] buf = new byte[8192];
		int len;
		while ((len = in.read(buf)) > 0) {
		    out.write(buf, 0, len);
		    if (buffer != null) {
			synchronized (buffer) {
			    buffer.setLength(0);
			    buffer.append(out.toString());
			}
		    }
		}
	    } finally {
		in.close();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
