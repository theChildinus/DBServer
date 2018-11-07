package org.wso2.carbon.registry.samples.filesampleutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

import org.wso2.carbon.registry.app.RemoteRegistry;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.utils.RegistryClientUtils;

public class FileSampleUtil {

	public static void main(String[] args) throws Exception {
		String registryURL;
		String userName;
		String passWord;
		String fromPath;
		String toPath;
		String args0 = null;
		String key_store;
		RemoteRegistry remoteRegistry;

		if (args.length > 0) {
			args0 = args[0];
		}

		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);

		System.out.println("Path to registry key_store file:");
		key_store = br.readLine();

		System.out.println("Registry URL :");

		registryURL = br.readLine();

		System.out.println("User Name :");

		userName = br.readLine();

		if ("".equals(userName)) {
			userName = null;
		}
		System.out.println("Password :");
		passWord = br.readLine();

		if ("".equals(passWord)) {
			passWord = null;
		}

		System.out.println("From Path :");
		fromPath = br.readLine();
		if ("".equals(fromPath)) {
			throw new Exception(
					" From path can not be null , you have to provide the local file system location!!");
		}
		System.out.println("To Path :");
		toPath = br.readLine();

		System.setProperty("javax.net.ssl.trustStore", key_store);
		System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
		System.setProperty("javax.net.ssl.trustStoreType", "JKS");

		if (userName == null) {
			remoteRegistry = new RemoteRegistry(new URL(registryURL));
		} else {
			remoteRegistry = new RemoteRegistry(new URL(registryURL), userName,
					passWord);
		}

		if (args0 == null) {

			RegistryClientUtils.importToRegistry(new File(fromPath), toPath,
					remoteRegistry);

		} else {
			try {
				RegistryClientUtils.exportFromRegistry(new File(toPath),
						fromPath, remoteRegistry);
			} catch (RegistryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
