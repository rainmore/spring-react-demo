<script setup lang="ts">
import { onMounted, Ref, ref } from 'vue';
import { AppRoutePaths } from '../../../AppRoutes';
import Breadcrumb from '../../../components/Breadcrumb.vue';
import Pagination from '../../../components/Pagination.vue';
import { Page } from '../../../services/api/types';
import { bookService } from '../../../services/book/BookService';
import { Category } from '../../../services/book/types';
import { PaginationService } from '../../../services/PaginationService';
import { toastService } from '../../../services/ToastService';

const paginationService = new PaginationService()
const loading = ref(false)
const pageData: Ref<Page<Category> | null> = ref(null)

const breadcrumbList = [
  {
    url: AppRoutePaths.BOOK_LIST,
    name: 'Book'
  },
  {
    url: AppRoutePaths.BOOK_CATEGORY_LIST,
    name: 'Book Categories'
  },
]

onMounted(() => {
  bookService.findCategories(paginationService.getPageable())
    .then((data) => {
      pageData.value = data
      loading.value = false
    })
    .catch((error) => {
      toastService.error(error.message)
    })

})
</script>

<template>
  <Breadcrumb :links="breadcrumbList" />
  <h1 class="title">Book Categories</h1>
  <div class="box">
    <Loading v-if="loading" />
    <table v-else="loading" class="table is-fullwidth is-striped is-hoverable">
      <thead>
        <tr>
          <th class="col-id">#</th>
          <th>Name</th>
          <th class="col-control"></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(row, index) in pageData?.content" :key="`row-${index}`">
          <td>{{ row.id }}</td>
          <td>{{ row.name }}</td>
          <td>
            <!-- <a href="`/book/category/${row.id}/edit`"} title="Edit"
                class="button is-text is-light">
                <span class="icon">
                  <FontAwesomeIcon icon="pen-to-square" />
                </span>
              </a> -->
          </td>
        </tr>
      </tbody>
    </table>
    <Pagination v-if="pageData != null" :paginationService="paginationService" :page="pageData" />
  </div>
</template>