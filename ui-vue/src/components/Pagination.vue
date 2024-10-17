<script setup lang="ts">
import { PaginationService } from '../services/PaginationService';

interface Props {
  paginationService: PaginationService,
  page: Page<Any>
}

const { paginationService, page } = defineProps<Props>()
</script>

<template>
  <nav class="pagination is-centered" role="navigation" aria-label="pagination">
    <template v-if="page.first">
      <a class="pagination-previous is-disabled">&lt;&lt;</a>
      <a class="pagination-previous is-disabled">&lt;</a>
    </template>
    <template v-else="page.first">
      <a :href="paginationService.firstPagePath()" class="pagination-previous">
        &lt;&lt;
      </a>
      <a :href="paginationService.prevPagePath(page)" class="pagination-previous">
        &lt;
      </a>
    </template>

    <template v-if="page.last">
      <a class="pagination-next is-disabled">&gt;</a>
      <a class="pagination-next is-disabled">&gt;&gt;</a>
    </template>
    <template v-else="page.last">
      <a :href="paginationService.nextPagePath(page)" class="pagination-next">
        &gt;
      </a>
      <a :href="paginationService.lastPagePath(page)" class="pagination-next">
        &gt;&gt;
      </a>
    </template>
    <ul class="pagination-list">
      <li v-for="(number, index) in paginationService.getPaginationRage(page)" :key="`pagination-page-item-${number + 1}-${index}`">
        <template v-if="number == page.number ">
          <a class="pagination-link is-current" aria-label="`Page ${number + 1}`" aria-current="page">
            {{ number + 1 }}
          </a>
        </template>
        <template v-else="number == page.number ">
          <a :href="paginationService.buildPath(number)" class="pagination-link" aria-label="`Goto page ${number + 1}`">
            {{ number + 1 }}
          </a>
        </template>
      </li>
    </ul>
  </nav>
</template>