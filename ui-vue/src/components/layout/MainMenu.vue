<script setup lang="ts">
import { AppRoutePaths } from '../../AppRoutes';

const getActivePathClass = (path: string): string => {
  console.log($route.fullPath, path)
  return $route.fullPath === path ? 'is-active' : 'aaaa'
}
</script>


<template>
  <aside className="menu main-menu">
    <p className="menu-label">General</p>
    <ul className="menu-list">
      <li>
        <RouterLink :to="AppRoutePaths.DASHBOARD">
          Dashboard</RouterLink>
      </li>
      <li>
        <RouterLink :to="AppRoutePaths.ABOUT">Go to About</RouterLink>
      </li>
    </ul>
    <p className="menu-label">Books</p>
    <ul className="menu-list">
      <li>
        <RouterLink :to="AppRoutePaths.BOOK_LIST">
          Books
        </RouterLink>
      </li>
      <li>
        <RouterLink :to="AppRoutePaths.BOOK_CATEGORY_LIST">
          Categories
        </RouterLink>
      </li>
    </ul>
  </aside>

  <aside className="menu sub-menu menu-end is-hidden-mobile">
    <ul className="menu-list">
      <li>
        <a href="https://rainmore.com.au" target="_blank">
          Powered By Rainmore
        </a>
      </li>
    </ul>
  </aside>
</template>