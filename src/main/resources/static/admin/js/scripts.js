function createPagination(totalItems, currentPage, target, query) {
    const itemsPerPage = 10;
    const totalPages = Math.ceil(totalItems / itemsPerPage);
    if (!currentPage) {
        currentPage = 1;
    }

    const pagination = document.querySelector('.pagination');
    pagination.innerHTML = ''; // 초기화

    // Previous 버튼
    if (currentPage > 1) {
        const prevLi = document.createElement('li');
        prevLi.classList.add('page-item');
        const prevA = document.createElement('a');
        prevA.classList.add('page-link');
        prevA.innerText = 'Previous';
        prevA.href = `/admin/${target}?pageIndex=${currentPage - 1}&pageSize=${itemsPerPage}${query}`;
        prevLi.appendChild(prevA);
        pagination.appendChild(prevLi);
    }

    // 페이지 숫자 버튼 (최대 5개씩 표시)
    const maxPageButtons = 5;
    const startPage = Math.max(1, currentPage - Math.floor(maxPageButtons / 2));
    const endPage = Math.min(totalPages, startPage + maxPageButtons - 1);

    for (let i = startPage; i <= endPage; i++) {
        const pageLi = document.createElement('li');
        pageLi.classList.add('page-item');
        if (i === currentPage) {
            pageLi.classList.add('active');
        }

        const pageA = document.createElement('a');
        pageA.classList.add('page-link');
        pageA.innerText = i;
        pageA.href = `/admin/${target}?pageIndex=${i}&pageSize=${itemsPerPage}${query}`;
        pageLi.appendChild(pageA);
        pagination.appendChild(pageLi);
    }

    // Next 버튼
    if (currentPage < totalPages) {
        const nextLi = document.createElement('li');
        nextLi.classList.add('page-item');
        const nextA = document.createElement('a');
        nextA.classList.add('page-link');
        nextA.innerText = 'Next';
        nextA.href = `/admin/${target}?pageIndex=${currentPage + 1}&pageSize=${itemsPerPage}${query}`;
        nextLi.appendChild(nextA);
        pagination.appendChild(nextLi);
    }
}
