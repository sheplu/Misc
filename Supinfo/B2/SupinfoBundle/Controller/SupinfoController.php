<?php

namespace Nts\SupinfoBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Response;

use Nts\SupinfoBundle\Entity\Sta;
class SupinfoController extends Controller
{
    public function indexAction()
    {
        return $this->render('NtsSupinfoBundle:Supinfo:index.html.twig');
    }

    public function connexionAction()
    {
        $sta = new sta;

        $form = $this->createFormBuilder($sta)
            ->add('Email', 'text')
            ->add('Password', 'password')
            ->getForm();

        $request = $this->get('request');

        if( $request->getMethod() == 'POST' )
        {
            $form->bindRequest($request);

            if( $form->isValid() )
            {
                //$value = "Clément :D";
                $session = $this->getRequest()->getSession();
                //$session->set('foo', $value);
                $session->setFlash('STA', 'Clement :D');
                return $this->redirect($this->generateUrl('intervention'));
            }
        }

        return $this->render('NtsSupinfoBundle:Supinfo:connexion.html.twig', array(
            'form' => $form->createView(),
        ));
    }

    public function inscriptionAction()
    {
        $sta = new sta;

        $form = $this->createFormBuilder($sta)
            ->add('Firstname', 'text')
            ->add('Lastname', 'text')
            ->add('Email', 'text')
            ->add('Password', 'password')
            ->getForm();

        $request = $this->get('request');

        if( $request->getMethod() == 'POST' )
        {
            $form->bindRequest($request);

            if( $form->isValid() )
            {
                $em = $this->getDoctrine()->getEntityManager();
                $em->persist($sta);
                $em->flush();

                return $this->redirect($this->generateUrl('Supinfo_accueil'));
            }
        }

        return $this->render('NtsSupinfoBundle:Supinfo:inscription.html.twig', array(
            'form' => $form->createView(),
        ));
    }

    public function interventionAction()
    {
        $em = $this->getDoctrine()->getEntityManager();

        $entities = $em->getRepository('NtsSupinfoBundle:Intervention')->findAll();

        return $this->render('NtsSupinfoBundle:Supinfo:intervention.html.twig', array(
            'entities' => $entities
        ));
    }

    public function voirAction($id)
    {
        $em = $this->getDoctrine()->getEntityManager();

        $entity = $em->getRepository('NtsSupinfoBundle:Intervention')->find($id);

        if (!$entity) {
            throw $this->createNotFoundException('Unable to find Intervention entity.');
        }
        return $this->render('NtsSupinfoBundle:Supinfo:show_intervention.html.twig', array(
            'entity' => $entity
        ));
    }
}