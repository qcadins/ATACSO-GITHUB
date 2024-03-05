<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>Process Message Request</name>
   <tag></tag>
   <elementGuidId>0ace0406-d3b5-4db7-a991-f692aa7db418</elementGuidId>
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
      <webElementGuid>72af4410-00d9-4316-85a5-8dfb2a763cf5</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>text/plain</value>
      <webElementGuid>3241350e-8bb8-4ace-82fa-9262b53f41f1</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>AdInsKey</name>
      <type>Main</type>
      <value>${AdInsKey}</value>
      <webElementGuid>93d6bc51-0ea0-4e65-a9dc-5998047568d5</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>9.0.0</katalonVersion>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>${base_url}/v1/Callback/QueueHalosis</restUrl>
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
      <id>4094263b-2376-4fe5-b625-48562edcd34c</id>
      <masked>false</masked>
      <name>base_url</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.AdInsKey</defaultValue>
      <description></description>
      <id>d4775b0e-b746-45db-acfc-cc91f9845850</id>
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
