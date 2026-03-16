<script setup>
import {BApp, BNavbar, BNavbarNav, BNavItem, BNavText} from "bootstrap-vue-next";

</script>

<template>
  <BApp>
    <BNavbar fixed="top" variant="dark" class="w-100 bg-dark" container="fluid">
      <BNavbarNav class="me-auto">
        <BNavText>KOMPUTORNIXENSTREN</BNavText>
        <BNavItem to="/home">Strona główna</BNavItem>
        <BNavItem to="/laptops">Laptopy</BNavItem>
        <BNavItem to="/cpus">Procesory</BNavItem>
        <BNavItem to="/gpus">Karty graficzne</BNavItem>
        <BNavItem to="/ram">RAM</BNavItem>
        <BNavItem to="/motherboards">Płyty główne</BNavItem>
        <BNavItem to="/supplies">Zasilacze</BNavItem>
        <BNavItem to="/cooling">Chłodzenia</BNavItem>
        <BNavItem to="/preassembled">Gotowe stacje</BNavItem>
      </BNavbarNav>
    </BNavbar>

    <router-view/>

  </BApp>
</template>

<style>

</style>