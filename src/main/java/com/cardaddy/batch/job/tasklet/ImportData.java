package com.cardaddy.batch.job.tasklet;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public interface ImportData extends Closeable {
	InputStream getVehicles() throws IOException;
	InputStream getCustomers() throws IOException;
	boolean hasCustomers();
}
