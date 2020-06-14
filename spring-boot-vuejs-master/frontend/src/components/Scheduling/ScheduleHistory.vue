<template>
<h1>odiskfjlkoijsdhfhijofgdshjiodfsjifdsjiodfsiopjsdfijopfdsijop241312312</h1>
</template>

<script>
    import api from "../backend-api";
    export default {
        name: "AppointmentHistory",
        data () {
            return {

            }
        },
        mounted() {
           api.verifyAccount(localStorage.getItem("regUsername")).then(response=>{
               console.log(response);
           })
        }
    }
</script>

<style scoped>

</style>