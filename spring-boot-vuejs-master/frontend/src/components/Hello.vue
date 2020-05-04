<template>
  <div class="hello">
    <img src="./../assets/spring-boot-vuejs-logo.png">
    <h1>{{ hellomsg }}</h1>
    <h2>See the sources here: </h2>
    <ul>
      <li><a href="https://github.com/jonashackt/spring-boot-vuejs" target="_blank">github.com/jonashackt/spring-boot-vuejs</a></li>
    </ul>
    <h3>This site contains more stuff :)</h3>
    <ul>
        <li>HowTo call REST-Services:</li>
        <li><router-link to="/callservice">/callservice</router-link></li>
        <li>HowTo to play around with Bootstrap UI components:</li>
        <li><router-link to="/bootstrap">/bootstrap</router-link></li>
        <li>HowTo to interact with the Spring Boot database backend:</li>
        <li><router-link to="/user">/user</router-link></li>
        <li>Login to the secured part of the application</li>
        <li><router-link to="/login">/login</router-link></li>
        <li>A secured part of this application:</li>
        <li><router-link to="/protected">/protected</router-link></li>
        <li>Registration:</li>
        <li><router-link to="/staffregistration">/addStaffMember</router-link></li>
        <li>Registration:</li>
        <li><router-link to="/deleteStaff">/deleteStaffMember</router-link></li>
        <li>Add clinic administrators:</li>
        <li><router-link to="/regclinicadmin">/regclinicadmin</router-link></li>
        <li></li>
    </ul>
      <button class="button" ><router-link to="/register">REGISTER</router-link></button>
  </div>
</template>

<script>
export default {
  name: 'hello',
  props: { hellomsg: { type: String, required: true } }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
.button{

    background-color: lightslategray;
    width: 250px;
    outline-style: dot-dash;
    margin-left: 1000px;
    font-size: 40px;
}
ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
