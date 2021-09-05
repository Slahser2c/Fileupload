package com.oocl.fileupload.utlis;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

import java.io.FileInputStream;
import java.util.*;

/**
 * Namespace prefix mapper to map between namespaces and namespace prefix in
 * elements.
 * 
 * @author CHOWVI2
 * 
 */
public class NamespacePrefixMapperImpl extends NamespacePrefixMapper {

	public static final String PREFERRED_NAMESPACE_PREFIX = "prefix.pre.";
	public static final String CUSTOMIZED_NAMESPACE_PREFIX = "prefix.ns.";
	public static final String DEFAULT_NAMESPACE_PREFIX = "http://www.oocllogistics.com/";
	protected Map<String, String> prefixMap = new HashMap<String, String>();

	protected String[] preDeclaredNsUris;

	public NamespacePrefixMapperImpl() {
		this("jaxbmapper.properties");
	}

	public NamespacePrefixMapperImpl(String propertyFile) {
		super();
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(propertyFile));
			readConfiguration(properties);
		} catch (Exception e) {
		}
	}

	public NamespacePrefixMapperImpl(Properties properties) {
		super();
		readConfiguration(properties);
	}



	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		if (prefixMap.containsKey(namespaceUri)) {
			return prefixMap.get(namespaceUri);
		} else if (namespaceUri.startsWith(DEFAULT_NAMESPACE_PREFIX)) {
			return namespaceUri.substring(DEFAULT_NAMESPACE_PREFIX.length());
		}

		return suggestion;
	}

	public String[] getPreDeclaredNamespaceUris() {
		return preDeclaredNsUris;
	}

	/**
	 * Read mapping from supplied configuration.
	 *
	 * @param config configuration containing namespace mappings
	 */
	private void readConfiguration(Properties config) {
		Enumeration<String> propertyNames = (Enumeration<String>) config.propertyNames();
		List<String> preferredNamespaces = new ArrayList<String>();

		while (propertyNames.hasMoreElements()) {
			String propertyName = propertyNames.nextElement().trim();
			String propertyValue = config.getProperty(propertyName, "").trim();
			if (propertyName.startsWith(PREFERRED_NAMESPACE_PREFIX)) {
				String namespace = propertyName.substring(PREFERRED_NAMESPACE_PREFIX.length());
				preferredNamespaces.add(namespace);
				prefixMap.put(namespace, propertyValue);
			} else if (propertyName.startsWith(CUSTOMIZED_NAMESPACE_PREFIX)) {
				String namespace = propertyName.substring(CUSTOMIZED_NAMESPACE_PREFIX.length());
				prefixMap.put(namespace, propertyValue);
			}
		}

		preDeclaredNsUris = preferredNamespaces.toArray(new String[preferredNamespaces.size()]);
	}

}
