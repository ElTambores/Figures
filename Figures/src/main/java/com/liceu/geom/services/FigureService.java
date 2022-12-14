package com.liceu.geom.services;

import com.liceu.geom.DAO.FigureDao;
import com.liceu.geom.DAO.FigureDaoImpl;
import com.liceu.geom.model.Figure;
import com.liceu.geom.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FigureService {
    FigureDao figureDao = new FigureDaoImpl();

    public Boolean newFigure(User user, String name, int x, int y, int size, String color, String shape) {
        //Si el usuario no introduce nombre se genera uno al azar comprobando que no exista ese nombre de figura para ese usuario.
        if (name.equals("")) {
            name = shape + (int) ((Math.random()) * 100);
            Figure figure = new Figure(user, name, x, y, size, color, shape);
            Boolean success = figureDao.saveFigure(figure);
            while (!success) {
                name = shape + (int) ((Math.random()) * 100);
                figure = new Figure(user, name, x, y, size, color, shape);
                success = figureDao.saveFigure(figure);
            }
            return true;
        }
        Figure figure = new Figure(user, name, x, y, size, color, shape);
        return figureDao.saveFigure(figure);
    }

    public List<Figure> getAllFigures() {
        return figureDao.getAllFigures();
    }

    public List<Figure> getUserFigures(User user) {
        return figureDao.getUserFigures(user);
    }

    public Boolean deleteFigure(int figureID, User currentUser) {
        //Se verifica que haya un usuario activo.
        if (currentUser == null) return false;
        //Se obtiene la figura y si existe se intenta borrar.
        Figure figure = getFigureByID(figureID);
        if (figure == null) return false;
        //Se verifica que el usuario que quiere borrar la figura sea el activo
        if (figure.getUser().getId() != currentUser.getId()) return false;
        return figureDao.deleteFigure(figureID);
    }

    public Figure getFigureByID(int figureID) {
        return figureDao.getFigureByID(figureID);
    }

    public List<Figure> getFiguresByNames(List<Figure> figureList, String figureName) {
        List<Figure> filteredFigureList = new ArrayList<>();
        for (Figure figure : figureList) {
            if (figure.getName().equals(figureName)) filteredFigureList.add(figure);
        }
        return filteredFigureList;
    }
}
