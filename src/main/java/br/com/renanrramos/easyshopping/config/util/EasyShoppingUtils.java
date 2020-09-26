/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.config.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.renanrramos.easyshopping.exception.EasyShoppingException;

/**
 * @author renan.ramos
 *
 */
@Component
public class EasyShoppingUtils {

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public EasyShoppingUtils() {
		// Intentionally empty
	}

	public String encodePassword(String password) {
		return bcryptEncoder.encode(password);
	}

	public boolean verifyPassword(String password, String encodedPassword) {
		return bcryptEncoder.matches(password, encodedPassword);
	}

	public byte[] compressImageBytes(byte[] productImage) throws EasyShoppingException {
		Deflater deflater = new Deflater();
		deflater.setInput(productImage);
		deflater.finish();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(productImage.length);
		try {
			writeProductImageBytesToOutputStream(outputStream, deflater, null);			
			outputStream.close();
		} catch (IOException e) {
			throw new EasyShoppingException(e.getLocalizedMessage());
		}

		return outputStream.toByteArray();
	}

	public byte[] decompressImageBytes(byte[] productImage) {
		Inflater inflater = new Inflater();
		inflater.setInput(productImage);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(productImage.length);
		try {
			writeProductImageBytesToOutputStream(outputStream, null, inflater);
			outputStream.close();
		} catch (IOException e) {
			// do nothing
		}
		return outputStream.toByteArray();
	}

	private void writeProductImageBytesToOutputStream(ByteArrayOutputStream outputStream, Deflater deflater, Inflater inflater) {
		byte[] buffer = new byte[1024];

		if (deflater != null) {
			deflateProductImage(deflater, outputStream, buffer);
		} else if (inflater != null ){
			inflateProductImage(inflater, outputStream, buffer);
		}		
	}

	private void deflateProductImage(Deflater deflater, ByteArrayOutputStream outputStream, byte[] buffer) {
		while (!deflater.finished()) {
			int counteDeflater = deflater.deflate(buffer);
			outputStream.write(buffer, 0, counteDeflater);
		}		
	}

	private void inflateProductImage(Inflater inflater, ByteArrayOutputStream outputStream, byte[] buffer) {
		while (!inflater.finished()) {
			try {
				int counteDeflater = inflater.inflate(buffer);
				outputStream.write(buffer, 0, counteDeflater);
			} catch (DataFormatException e) {
				break;
			}
		}
	}
}
