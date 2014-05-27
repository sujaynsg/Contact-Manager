<h2>Contact-Manager API<h2>

Contact Manager API is used to add, edit, update and delete contacts.

This API is based on the REST Principles.

<h4>Getting Started</h4>

<table>
  <tr> 
    <td><a href="#listcontacts" title="Listcontacts"> List Contacts </a><br> GET/http://localhost:8080/contactmanger/contacts/ </td>
    <td> List all the contacts </td>
  </tr>
  <tr> 
    <td> Get Contact <br> GET/http://localhost:8080/contactmanger/contacts/:contactid </td>
    <td> Get details of the contact </td>
  </tr>
  <tr> 
    <td> Create Contact <br> POST/http://localhost:8080/contactmanger/contacts/ </td>
    <td> Create contact with the given information </td>
  </tr>
  <tr> 
     <td> Update Contact <br> PUT/http://localhost:8080/contactmanger/contacts/:contactid </td>
    <td> Update details of the contact </td>
  </tr>
  <tr> 
    <td> Delete Contact <br> Delete/http://localhost:8080/contactmanger/contacts/ </td>
    <td> Delete details of the contact</td>
  </tr>
</table>
<h3 id="listcontacts">List contacts</h3>
<pre><code>GET  /contacts </code></pre>
