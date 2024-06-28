<script>
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('task-search-form').addEventListener('submit', async function(event) {
        event.preventDefault();

        let searchTerm = document.getElementById('search-input').value.trim();

        try {
            const response = await fetch('/api/searchTasks', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    searchQuery: searchTerm
                })
            });

            if (response.ok) {
                const data = await response.json();
                displayResults(data);
            } else {
                const errorText = await response.text();
                console.error('Error response text:', errorText);
                alert('Error searching tasks. Please try again.');
            }
        } catch (error) {
            console.error('An error occurred:', error);
            alert('An error occurred. Please try again.');
        }
    });

    function displayResults(results) {
        const searchResultsDiv = document.getElementById('search-results');
        searchResultsDiv.innerHTML = '';

        if (results.length === 0) {
            searchResultsDiv.innerHTML = `<div class="search-results-container">
                                            <p style="font-weight: bold;">No results found.</p>
                                          </div>`;
        } else {
            const resultList = results.map(result => `<li>${result.name}</li>`).join('');
            searchResultsDiv.innerHTML = `<div class="search-results-container">
                                            <p style="font-weight: bold;">Search Results:</p>
                                            <ul>${resultList}</ul>
                                          </div>`;
        }
    }
});
</script>








