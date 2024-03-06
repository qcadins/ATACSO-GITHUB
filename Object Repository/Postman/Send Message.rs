<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>Send Message</name>
   <tag></tag>
   <elementGuidId>3309400c-6063-4cd0-a0c7-0ebd4e1f28eb</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>false</autoUpdateContent>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\n  \&quot;requestDateTime\&quot;: \&quot;${requestDateTime}\&quot;,\n  \&quot;rowVersion\&quot;: \&quot;${rowVersion}\&quot;,\n  \&quot;receiverPhone\&quot;: \&quot;${receiverPhone}\&quot;,\n  \&quot;referenceNo\&quot;: \&quot;${referenceNo}\&quot;,\n  \&quot;templateCode\&quot;: \&quot;${templateCode}\&quot;,\n  \&quot;headerParam\&quot;: ${headerParam},\n  \&quot;bodyParam\&quot;: ${bodyParam},\n  \&quot;buttonParam\&quot;: ${buttonParam}\n}&quot;,
  &quot;contentType&quot;: &quot;text/plain&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>accept</name>
      <type>Main</type>
      <value>*/*</value>
      <webElementGuid>8623109b-5e12-41a3-8795-536dffdabd9e</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json-patch+json</value>
      <webElementGuid>37366ddc-3085-4b34-9968-d7cb3c8edc72</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>AdInsKey</name>
      <type>Main</type>
      <value>${AdInsKey}</value>
      <webElementGuid>142094e7-b95b-4293-a72a-ffed2e6cc584</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>9.0.0</katalonVersion>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>${base_url}/v1/Message/Send</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>-1</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <variables>
      <defaultValue>GlobalVariable.base_url</defaultValue>
      <description></description>
      <id>b0dfe2dd-deaf-4d92-9a5d-e506bba00b8c</id>
      <masked>false</masked>
      <name>base_url</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.AdInsKey</defaultValue>
      <description></description>
      <id>b6a1e446-9104-40e9-8eff-fbeb8751b701</id>
      <masked>false</masked>
      <name>AdInsKey</name>
   </variables>
   <verificationScript>import static org.assertj.core.api.Assertions.*

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

RequestObject request = WSResponseManager.getInstance().getCurrentRequest()

ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
