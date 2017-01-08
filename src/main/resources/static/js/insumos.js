$(document).ready(function () {
  var navListItems = $('div.setup-panel div a'),
          allWells = $('.setup-content'),
          allNextBtn = $('.nextBtn');

  allWells.hide();

  navListItems.click(function (e) {
      e.preventDefault();
      var $target = $($(this).attr('href')),
          $item = $(this);
      
      if (!$item.is("[disabled]")) {
          navListItems.removeClass('btn-primary').addClass('btn-default');
          $item.addClass('btn-primary');
          allWells.hide();
          $target.show();
          $target.find('input:eq(0)').focus();
      }
  });

  allNextBtn.click(function(){
      
      var curStep = $(this).closest(".setup-content"),
          curStepBtn = curStep.attr("id"),
          nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
          curInputs = curStep.find("input[type='text'],input[type='url'],input[type='number']"),
          isValid = true;

      $(".form-group").removeClass("has-error");
      for(var i=0; i<curInputs.length; i++){
          if (!curInputs[i].checkValidity()){
              isValid = false;
              $(curInputs[i]).closest(".form-group").addClass("has-error");
              $(curInputs[i]).closest(".form-group").addClass("has-feedback");
              $(curInputs[i]).parent().find(".with-errors").empty();
              $(curInputs[i]).parent().find(".with-errors").append("Campo Inválido");
          }
      }

      if (isValid)
          nextStepWizard.removeAttr('disabled').trigger('click');
  });
  $('div.setup-panel div a.btn-primary').trigger('click');
});

$('#submitbtn').submit(function e(){
   console.log("SUBMIT!!")
   window.location.replace("acces/calcular");
});

$('#ActualizarVEV').click(function (e){
    investmentAmount = Number(document.getElementById('valor').value);
    annualInterestRate = Number(document.getElementById('interes').value);
    numberOfYears = Number(document.getElementById('periodo').value);
    console.log(Math.pow(1 + (annualInterestRate), numberOfYears));
    console.log(investmentAmount);
    console.log(investmentAmount * Math.pow(1 + (annualInterestRate), numberOfYears));
    document.getElementById('VEV').value = parseInt(investmentAmount * Math.pow(1 + (annualInterestRate), numberOfYears));
});