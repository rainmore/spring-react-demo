<script setup lang="ts">
import { AppRoutePaths } from '../../AppRoutes';
import { authService } from '../../services/auth/auth-service';
import { toastService } from '../../services/toast-service';
import { UrlService } from '../../services/url-service';

const urlService = new UrlService()

const onLogout = () => {
  authService.resetAuthContext();
  toastService.info("You've logged out")
  urlService.redirect(AppRoutePaths.AUTH_LOGIN)
}

const pageTitle = import.meta.env.VITE_APP_TITLE

const currentUser = authService.getAuthContext().value?.currentUser

</script>

<template>
  <nav class="navbar is-flex-tablet is-primary" role="navigation" aria-label="main navigation">
    <div class="navbar-brand">
      <a class="navbar-item title" href="#">
        {{ pageTitle }}
      </a>
    </div>

    <div class="navbar-end" v-if="authService.isAuthenticated()">
      <div class="navbar-item has-dropdown is-hoverable">
        <a class="navbar-link">{{ currentUser.account.firstname }} {{ currentUser.account.lastname }}</a>

        <div class="navbar-dropdown is-right">
          <a class="navbar-item">Change Profile</a>
          <a class="navbar-item">Update password</a>
          <hr class="navbar-divider" />
          <a class="navbar-item" @click="onLogout">Logout</a>
        </div>
      </div>
    </div>
  </nav>
</template>