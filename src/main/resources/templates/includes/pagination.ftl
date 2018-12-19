<nav aria-label="Page navigation">
  <ul class="pagination justify-content-center">
    <#if totalPages?? && (totalPages >= 1)>
        <#assign pageIndex = totalPages - 1>

        <#list 0..pageIndex as currentPage>
            <li class="page-item <#if pageNumber! == currentPage>active</#if>">
                <a class="page-link" href="/view/list?page=${currentPage}">${currentPage + 1}</a>
            </li>
        </#list>
    </#if>
  </ul>
</nav>
