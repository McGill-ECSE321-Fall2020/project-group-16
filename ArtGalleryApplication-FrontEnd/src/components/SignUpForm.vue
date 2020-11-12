<template>
  <div id="sign-up-form">
    <form action=""><div class="row my-3 px-3 pr-3">
      <div class="col-sm mx-0 px-0"><input
          type="text"
          name="first-name"
          id="first-name"
          placeholder="First Name"
          v-model="firstName"
      /></div><div class="col-sm mx-0 px-0 pl-3"><input type="text" name="last-name" id="last-name" placeholder="Last Name" v-model="lastName"
      /></div>
        
      
        
        
    </div>
    <div class="row my-3 px-3"><input type="text" name="username" id="username" placeholder="Username" v-model="username" /></div>
    <div class="row my-3 px-3"><input type="email" name="email" id="email" placeholder="Email" v-model="email" /></div>
    <div class="row my-3 px-3"><input
      type="password"
      name="password"
      id="password"
      placeholder="Password"
      v-model="password"
    /></div>
    
    
    

    <div class="button px-3 py-1 mt-1 mb-3" type="submit" @click="signup">Sign Up</div></form>
    
  </div>
</template>
<script src="../js/signup.js">

</script>
<style scoped src="../css/signInForm.css">

</style>
