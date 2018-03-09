package com.github.bohnman.core.io;

import com.github.bohnman.core.lang.CoreAssert;

import java.io.IOException;
import java.io.OutputStream;

public abstract class OutputStreamWrapper extends OutputStream {

    private final OutputStream wrapped;

    public OutputStreamWrapper(OutputStream wrapped) {
        this.wrapped = CoreAssert.notNull(wrapped);
    }

    @Override
    public void write(int b) throws IOException {
        wrapped.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        wrapped.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        wrapped.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        wrapped.flush();
    }

    @Override
    public void close() throws IOException {
        wrapped.close();
    }

    public OutputStream getWrapped() {
        return wrapped;
    }
}
