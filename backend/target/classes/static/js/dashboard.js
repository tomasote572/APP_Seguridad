document.addEventListener('DOMContentLoaded', function() {
    const ctx = document.getElementById('severityChart');
    if (ctx) {
        const critical = parseInt(ctx.getAttribute('data-critical')) || 0;
        const high = parseInt(ctx.getAttribute('data-high')) || 0;
        const medium = parseInt(ctx.getAttribute('data-medium')) || 0;
        const low = parseInt(ctx.getAttribute('data-low')) || 0;

        new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ['Crítica', 'Alta', 'Media', 'Baja'],
                datasets: [{
                    data: [critical, high, medium, low],
                    backgroundColor: ['#6f42c1', '#dc3545', '#ffc107', '#198754'],
                    borderWidth: 0
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'bottom' }
                },
                cutout: '70%'
            }
        });
    }
});
