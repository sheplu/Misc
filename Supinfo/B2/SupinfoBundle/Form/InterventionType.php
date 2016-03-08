<?php

namespace Nts\SupinfoBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilder;

class InterventionType extends AbstractType
{
    public function buildForm(FormBuilder $builder, array $options)
    {
        $builder
            ->add('subject')
            ->add('start')
            ->add('end')
            ->add('status')
            ->add('sta_id')
            ->add('campus_id')
            ->add('active')
        ;
    }

    public function getName()
    {
        return 'nts_supinfobundle_interventiontype';
    }
}
