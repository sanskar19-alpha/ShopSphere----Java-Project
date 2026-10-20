// ShopSphere client-side helpers (progressive enhancement only - the server validates everything again).
document.addEventListener('DOMContentLoaded', function () {
  // Confirm before destructive actions: <form data-confirm="Are you sure?">
  document.querySelectorAll('form[data-confirm]').forEach(function (f) {
    f.addEventListener('submit', function (e) {
      if (!window.confirm(f.getAttribute('data-confirm'))) e.preventDefault();
    });
  });
  // Auto-submit selects marked with data-autosubmit
  document.querySelectorAll('select[data-autosubmit]').forEach(function (s) {
    s.addEventListener('change', function () { s.form.submit(); });
  });
  // Checkout: show only the fields of the selected payment method
  var methods = document.querySelectorAll('input[name="paymentMethod"]');
  function togglePayment() {
    var chosen = document.querySelector('input[name="paymentMethod"]:checked');
    document.querySelectorAll('[data-pay]').forEach(function (el) {
      el.classList.toggle('d-none', !chosen || el.getAttribute('data-pay') !== chosen.value);
    });
  }
  methods.forEach(function (m) { m.addEventListener('change', togglePayment); });
  togglePayment();
  // Auto-dismiss success alerts
  document.querySelectorAll('.alert-success.auto-dismiss').forEach(function (a) {
    setTimeout(function () { a.classList.remove('show'); a.remove(); }, 4000);
  });
});
