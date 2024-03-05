<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>Message Callback</name>
   <tag></tag>
   <elementGuidId>b70cb41a-3484-47f5-bf65-6be7595b0ad6</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>false</autoUpdateContent>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\n  \&quot;requestDateTime\&quot;: \&quot;${requestDateTime}\&quot;,\n  \&quot;rowVersion\&quot;: \&quot;${rowVersion}\&quot;,\n  \&quot;data\&quot;: {\n    \&quot;from_phone_number\&quot;: \&quot;${from_phone_number}\&quot;,\n    \&quot;to_phone_number\&quot;: \&quot;${to_phone_number}\&quot;,\n    \&quot;id\&quot;: \&quot;${id}\&quot;,\n    \&quot;conversation_id\&quot;: \&quot;${conversation_id}\&quot;,\n    \&quot;template_name\&quot;: \&quot;${template_name}\&quot;,\n    \&quot;status\&quot;: \&quot;${status}\&quot;,\n    \&quot;timestamp\&quot;: ${timestamp},\n    \&quot;agent_name\&quot;: \&quot;${agent_name}\&quot;,\n    \&quot;created_time\&quot;: \&quot;${created_time}\&quot;,\n    \&quot;action_id\&quot;: \&quot;${action_id}\&quot;,\n    \&quot;message\&quot;: \&quot;${message}\&quot;,\n    \&quot;error\&quot;: \&quot;${error}\&quot;\n  }\n}&quot;,
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
      <webElementGuid>4edbe417-a7ee-489d-bb6b-cbd3e6a7e71b</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>text/plain</value>
      <webElementGuid>62e8303b-1722-4f97-b5da-c850e6e7cec5</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>AdInsKey</name>
      <type>Main</type>
      <value>${AdInsKey}</value>
      <webElementGuid>19323a02-8bba-4f8a-aa23-960ac1ee618f</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>9.0.0</katalonVersion>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>${base_url}/v1/Callback/Halosis</restUrl>
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
      <id>e418a7b9-aa37-447d-ae5a-04dc09463fdf</id>
      <masked>false</masked>
      <name>base_url</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.AdInsKey</defaultValue>
      <description></description>
      <id>fa4e92fd-9bf2-486b-8076-fe60f954c71a</id>
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
