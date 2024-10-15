<script setup lang="ts">
import { useRouter } from 'vue-router';
import { toastService } from '../services/toast-service'


const router = useRouter()

const goToAbout = () => {
    toastService.success('Info')
}

</script>

<template>
    <h1>Welcome to my website</h1>
    <p>Here's some content for the homepage</p>

    <div class="buttons">
        <button class="button is-info" @click="goToAbout">
            Info
        </button>
        <button class="button is-success">
            Success
        </button>
        <button class="button is-warning">
            Warning
        </button>
        <button class="button is-danger">
            Danger
        </button>
        <button class="button is-primary">Primary</button>
        <button class="button is-link">Link</button>
        <button class="button is-info">Info</button>
        <button class="button is-success">Success</button>
        <button class="button is-warning">Warning</button>
        <button class="button is-danger">Danger</button>
        <button class="button is-black">Black</button>
        <button class="button is-white">White</button>
        <button class="button is-dark">Dark</button>
        <button class="button is-light">Light</button>
    </div>
</template>