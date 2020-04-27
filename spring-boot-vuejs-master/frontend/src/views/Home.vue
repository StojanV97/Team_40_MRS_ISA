<template>
  <MedicalStaffRegistrationForm />
</template>

<script>
  import HelloWorld from '../components/HelloWorld'
  import MedicalStaffRegistrationForm from "../components/MedicalStaffRegistrationForm";

  export default {
    name: 'Home',

    components: {
      MedicalStaffRegistrationForm,
      HelloWorld,
    },
  }
</script>
