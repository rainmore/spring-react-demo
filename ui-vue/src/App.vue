<script setup lang="ts">
import MainMenu from './components/layout/MainMenu.vue';
import MainNav from './components/layout/MainNav.vue';
import { authService } from './services/auth/AuthService';


</script>

<template>
    <MainNav />
    <section className="main-content columns is-fullheight">
        <div className="column is-2 is-narrow-mobile is-fullheight section">
            <MainMenu v-if="authService.isAuthenticated()" />
        </div>

        <div className="container column is-10 section">
            <RouterView />
        </div>
    </section>
</template>