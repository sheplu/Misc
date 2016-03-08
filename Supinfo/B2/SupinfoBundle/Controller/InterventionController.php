<?php

namespace Nts\SupinfoBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

use Nts\SupinfoBundle\Entity\Intervention;
use Nts\SupinfoBundle\Form\InterventionType;

/**
 * Intervention controller.
 *
 */
class InterventionController extends Controller
{
    public function indexAction()
    {
        return $this->render('NtsSupinfoBundle:Intervention:index.html.twig');
    }

    public function displayAction()
    {
        $em = $this->getDoctrine()->getEntityManager();

        $entities = $em->getRepository('NtsSupinfoBundle:Intervention')->findAll();

        return $this->render('NtsSupinfoBundle:Intervention:intervention.html.twig', array(
            'entities' => $entities
        ));
    }

    public function listAction()
    {
        $em = $this->getDoctrine()->getEntityManager();

        $entities = $em->getRepository('NtsSupinfoBundle:Intervention')->findAll();

        return $this->render('NtsSupinfoBundle:Intervention:intervention.html.twig', array(
            'entities' => $entities
        ));
    }

    public function showAction($id)
    {
    $em = $this->getDoctrine()->getEntityManager();

    $entity = $em->getRepository('NtsSupinfoBundle:Intervention')->find($id);

    if (!$entity) {
    throw $this->createNotFoundException('Unable to find Intervention entity.');
    }

    $deleteForm = $this->createDeleteForm($id);

    return $this->render('NtsSupinfoBundle:Intervention:show.html.twig', array(
    'entity'      => $entity,
    ));

    }

    public function showlistAction($id)
    {
        $em = $this->getDoctrine()->getEntityManager();

        $entity = $em->getRepository('NtsSupinfoBundle:Intervention')->find($id);

        if (!$entity) {
            throw $this->createNotFoundException('Unable to find Intervention entity.');
        }

        $deleteForm = $this->createDeleteForm($id);

        return $this->render('NtsSupinfoBundle:Intervention:show.html.twig', array(
            'entity'      => $entity,
            'delete_form' => $deleteForm->createView(),

        ));
    }

    public function newAction()
    {
        $entity = new Intervention();
        $form   = $this->createForm(new InterventionType(), $entity);

        return $this->render('NtsSupinfoBundle:Intervention:new.html.twig', array(
            'entity' => $entity,
            'form'   => $form->createView()
        ));
    }

    public function createAction()
    {
        $entity  = new Intervention();
        $request = $this->getRequest();
        $form    = $this->createForm(new InterventionType(), $entity);
        $form->bindRequest($request);

        if ($form->isValid()) {
            $em = $this->getDoctrine()->getEntityManager();
            $em->persist($entity);
            $em->flush();

            return $this->redirect($this->generateUrl('intervention_show', array('id' => $entity->getId())));
            
        }

        return $this->render('NtsSupinfoBundle:Intervention:new.html.twig', array(
            'entity' => $entity,
            'form'   => $form->createView()
        ));
    }

    public function editAction($id)
    {
        $em = $this->getDoctrine()->getEntityManager();

        $entity = $em->getRepository('NtsSupinfoBundle:Intervention')->find($id);

        if (!$entity) {
            throw $this->createNotFoundException('Unable to find Intervention entity.');
        }

        $editForm = $this->createForm(new InterventionType(), $entity);
        $deleteForm = $this->createDeleteForm($id);

        return $this->render('NtsSupinfoBundle:Intervention:edit.html.twig', array(
            'entity'      => $entity,
            'edit_form'   => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    public function updateAction($id)
    {
        $em = $this->getDoctrine()->getEntityManager();

        $entity = $em->getRepository('NtsSupinfoBundle:Intervention')->find($id);

        if (!$entity) {
            throw $this->createNotFoundException('Unable to find Intervention entity.');
        }

        $editForm   = $this->createForm(new InterventionType(), $entity);
        $deleteForm = $this->createDeleteForm($id);

        $request = $this->getRequest();

        $editForm->bindRequest($request);

        if ($editForm->isValid()) {
            $em->persist($entity);
            $em->flush();

            return $this->redirect($this->generateUrl('intervention_edit', array('id' => $id)));
        }

        return $this->render('NtsSupinfoBundle:Intervention:edit.html.twig', array(
            'entity'      => $entity,
            'edit_form'   => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    public function deleteAction($id)
    {
        $form = $this->createDeleteForm($id);
        $request = $this->getRequest();

        $form->bindRequest($request);

        if ($form->isValid()) {
            $em = $this->getDoctrine()->getEntityManager();
            $entity = $em->getRepository('NtsSupinfoBundle:Intervention')->find($id);

            if (!$entity) {
                throw $this->createNotFoundException('Unable to find Intervention entity.');
            }

            $em->remove($entity);
            $em->flush();
        }

        return $this->redirect($this->generateUrl('intervention'));
    }

    private function createDeleteForm($id)
    {
        return $this->createFormBuilder(array('id' => $id))
            ->add('id', 'hidden')
            ->getForm()
        ;
    }

    private function logout()
    {
        session_unset();
        session_destroy();
        return $this->render('NtsSupinfoBundle:Supinfo:index.html.twig');
    }
}
