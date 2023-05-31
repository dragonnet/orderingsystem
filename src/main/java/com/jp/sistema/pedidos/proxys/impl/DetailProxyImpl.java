package com.jp.sistema.pedidos.proxys.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.jp.sistema.pedidos.model.entity.detail.Detail;
import com.jp.sistema.pedidos.model.entity.detail.Detalle;
import com.jp.sistema.pedidos.proxys.iDetailProxy;

@Service
public class DetailProxyImpl implements iDetailProxy {
	private Gson gson = new Gson();
	
	@Autowired
	private CloseableHttpClient httpClient;

	@Override
	public ResponseEntity<Detail> save(Detalle detalle) throws IOException {
		
		String json = gson.toJson(detalle);
		// Crear solicitud POST
        HttpPost httpPost = new HttpPost("http://20.55.200.64:8048/DynamicsNAV110/ODataV4/Company('Laboratorio%20Pharmalat%2C%20S.A.')/Pedido_venta_linea");

        // Configurar el encabezado de la solicitud
        httpPost.setHeader("Content-Type", "application/json");

        // Establecer el cuerpo de la solicitud como una entidad de cadena JSON
        HttpEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);

        // Ejecutar la solicitud HTTP POST
        CloseableHttpResponse response = httpClient.execute(httpPost);
        
     // Obtener la respuesta del servidor
        HttpEntity responseEntity = response.getEntity();
        String responseBody = EntityUtils.toString(responseEntity);
		String newEntity = responseBody.replaceAll("@odata.","");
		Detail detail = gson.fromJson(newEntity, Detail.class);
		return ResponseEntity.ok(detail);
	}
}
